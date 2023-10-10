package com.github.javarushcommunity.jrtb.job;

import com.github.javarushcommunity.jrtb.service.FindNewArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@RequiredArgsConstructor
@Component
public class FindNewArticleJob {
    private final FindNewArticleService findNewArticleService;
//(fixedRateString = "${bot.recountNewArticleFixedRate}")
    @Scheduled(fixedRateString = "${bot.recountNewArticleFixedRate}")
    public void findNewArticles() {
        LocalDateTime start = LocalDateTime.now();
        log.info("Find new article job started.");

        findNewArticleService.findNewArticles();

        LocalDateTime end = LocalDateTime.now();
        log.info("Find new articles job finished. Took seconds: {}",
                end.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC));
    }
}
