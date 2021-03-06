package com.blogspot.sontx.bottle.server.model.repository.firebase;

import com.blogspot.sontx.bottle.server.model.bean.DeleteResult;
import com.blogspot.sontx.bottle.server.model.bean.chat.*;
import com.blogspot.sontx.bottle.server.model.repository.ChatRepository;
import com.blogspot.sontx.bottle.server.utils.BeanUtils;
import com.google.firebase.database.*;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Repository
@Log4j
public class FirebaseChatRepository implements ChatRepository {
    private final FirebaseManager firebaseManager;

    @Autowired
    public FirebaseChatRepository(FirebaseManager firebaseManager) {
        this.firebaseManager = firebaseManager;
    }

    @Override
    public CreateChannelResult createChannel(String userId1, String userId2) {
        // setup channel members
        String channelId = createChannelMembers(userId1, userId2);
        // setup channel detail
        createChannelDetail(channelId);
        // finally, setup user channel
        createUserChannel(channelId, userId1);
        createUserChannel(channelId, userId2);

        CreateChannelResult createChannelResult = new CreateChannelResult();
        createChannelResult.setId(channelId);
        return createChannelResult;
    }

    @Override
    public void deleteExpiredChannels(long beforeMillis) {
        DatabaseReference channelDetailsRef = firebaseManager.getReference("channel_details");
        channelDetailsRef.orderByChild("timestamp").endAt(beforeMillis).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String channelId = snapshot.getKey();

                    firebaseManager.getReference("channel_members").child(channelId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                firebaseManager.getReference("user_channels").child(dataSnapshot1.getKey()).child(channelId).removeValue();
                            }
                            dataSnapshot.getRef().removeValue();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    firebaseManager.getReference("messages").child(channelId).removeValue();
                    snapshot.getRef().removeValue();// channel_details

                    log.debug("Removed channel: " + channelId);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public DeleteResult deleteChannel(String channelId) {
        firebaseManager.getReference("channel_details").child(channelId).removeValue();
        firebaseManager.getReference("messages").child(channelId).removeValue();
        firebaseManager.getReference("channel_members").child(channelId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    firebaseManager.getReference("user_channels").child(dataSnapshot1.getKey()).child(channelId).removeValue();
                }
                dataSnapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DeleteResult deleteResult = new DeleteResult();
        deleteResult.setChannelId(channelId);
        deleteResult.setDeleted(true);
        return deleteResult;
    }

    private String createChannelMembers(String userId1, String userId2) {
        ChannelMember channelMember1 = new ChannelMember();
        channelMember1.setId(userId1);

        Map<String, Object> objectMap1 = BeanUtils.toMap(channelMember1);
        objectMap1.put("timestamp", ServerValue.TIMESTAMP);

        ChannelMember channelMember2 = new ChannelMember();
        channelMember2.setId(userId2);

        Map<String, Object> objectMap2 = BeanUtils.toMap(channelMember2);
        objectMap2.put("timestamp", ServerValue.TIMESTAMP);

        final CountDownLatch countDownLatch = new CountDownLatch(2);

        DatabaseReference channelMembersRef = firebaseManager.getReference("channel_members").push();
        channelMembersRef.child(userId1).setValue(objectMap1, (databaseError, databaseReference) -> countDownLatch.countDown());
        channelMembersRef.child(userId2).setValue(objectMap2, (databaseError, databaseReference) -> countDownLatch.countDown());

        try {
            countDownLatch.await(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error(e);
        }

        return channelMembersRef.getKey();
    }

    private void createChannelDetail(String channelId) {
        ChannelDetail channelDetail = new ChannelDetail();
        channelDetail.setLastMessage("");
        channelDetail.setMessageType(ChatMessage.TYPE_NONE);

        Map<String, Object> objectMap = BeanUtils.toMap(channelDetail);
        objectMap.put("timestamp", ServerValue.TIMESTAMP);

        final Object lock = new Object();
        firebaseManager.getReference("channel_details").child(channelId).setValue(objectMap, (databaseError, databaseReference) -> {
            synchronized (lock) {
                lock.notify();
            }
        });

        synchronized (lock) {
            try {
                lock.wait(5000);
            } catch (InterruptedException e) {
                log.error(e);
            }
        }
    }

    private void createUserChannel(String channelId, String userId) {
        DatabaseReference userChannelsRef = firebaseManager.getReference("user_channels").child(userId).child(channelId);
        createUserChannel(userChannelsRef);
    }

    private String createUserChannel(DatabaseReference userChannelsRef) {
        Channel channel = new Channel();
        channel.setId(userChannelsRef.getKey());

        Map<String, Object> objectMap = BeanUtils.toMap(channel);
        objectMap.put("timestamp", ServerValue.TIMESTAMP);

        final Object lock = new Object();
        userChannelsRef.setValue(objectMap, (databaseError, databaseReference) -> {
            synchronized (lock) {
                lock.notify();
            }
        });

        synchronized (lock) {
            try {
                lock.wait(5000);
            } catch (InterruptedException e) {
                log.error(e);
            }
        }

        return userChannelsRef.getKey();
    }
}
