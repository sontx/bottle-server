package com.blogspot.sontx.bottle.server.model.repository.firebase;

import com.blogspot.sontx.bottle.server.model.entity.PublicProfileEntity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.database.*;
import com.google.firebase.tasks.Task;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Scope("singleton")
@Component
@Log4j
public final class FirebaseManager {
    private Resource serviceAccountKeyResource = new ClassPathResource("firebase.json");
    private FirebaseDatabase defaultDatabase;
    private FirebaseAuth firebaseAuth;

    private void setupFirebase() throws IOException {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredential(FirebaseCredentials.fromCertificate(serviceAccountKeyResource.getInputStream()))
                .setDatabaseUrl("https://bottle-e6f6e.firebaseio.com/")
                .build();

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
        defaultDatabase = FirebaseDatabase.getInstance(firebaseApp);
        firebaseAuth = FirebaseAuth.getInstance(firebaseApp);
    }

    String getUserIdByToken(String token) throws InterruptedException {
        final Task<FirebaseToken> firebaseTokenTask = firebaseAuth.verifyIdToken(token);
        final String[] userIds = {null};
        final Object lock = new Object();

        firebaseTokenTask.addOnCompleteListener(task -> {
            if (task.getException() == null) {
                FirebaseToken result = task.getResult();
                userIds[0] = result.getUid();
            } else {
                log.error(task.getException());
            }
            synchronized (lock) {
                lock.notify();
            }
        });
        synchronized (lock) {
            lock.wait(20000);
            return userIds[0];
        }
    }

    DatabaseReference getReference() {
        return defaultDatabase.getReference();
    }

    DatabaseReference getReference(String path) {
        return defaultDatabase.getReference(path);
    }

    PublicProfileEntity getPublicProfileById(String uid) throws InterruptedException {
        final PublicProfileEntity publicProfile = new PublicProfileEntity();
        publicProfile.setId(uid);

        final Object lock = new Object();

        defaultDatabase.getReference("public_profiles").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                publicProfile.setDisplayName(dataSnapshot.child("displayName").getValue(String.class));
                publicProfile.setAvatarUrl(dataSnapshot.child("avatarUrl").getValue(String.class));

                synchronized (lock) {
                    lock.notify();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                log.error(databaseError.toException());
                synchronized (lock) {
                    lock.notify();
                }
            }
        });

        synchronized (lock) {
            lock.wait(20000);
        }

        return StringUtils.isEmpty(publicProfile.getDisplayName()) ? null : publicProfile;
    }

    FirebaseManager() {
        try {
            setupFirebase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
