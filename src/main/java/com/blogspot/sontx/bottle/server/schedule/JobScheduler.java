package com.blogspot.sontx.bottle.server.schedule;

import com.blogspot.sontx.bottle.server.model.repository.ChatRepository;
import com.blogspot.sontx.bottle.server.model.service.message.RoomMessageService;
import lombok.extern.log4j.Log4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j
@Component
public final class JobScheduler {
    private final ChatRepository chatRepository;
    private final RoomMessageService roomMessageService;

    @Autowired
    public JobScheduler(ChatRepository chatRepository, RoomMessageService roomMessageService) {
        this.chatRepository = chatRepository;
        this.roomMessageService = roomMessageService;
    }

    @Scheduled(fixedDelay = 1000 * 60)// 1 minute
    public void deleteChatTask() {
        log.debug("deleting expired chat...");
        long beforeMillis = DateTime.now().minusHours(24).getMillis();
        chatRepository.deleteExpiredChannels(beforeMillis);
    }

    @Scheduled(fixedDelay = 1000 * 60)// 1 minute
    public void deleteMessageTask() {
        log.debug("deleting expired message...");
        long beforeMillis = DateTime.now().minusHours(24).getMillis();
        roomMessageService.deleteExpiredMessages(beforeMillis);
    }
}
