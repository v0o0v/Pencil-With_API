package com.pencilwith.apiserver.crew;

import com.pencilwith.apiserver.domain.entity.CrewRecruit;
import com.pencilwith.apiserver.domain.entity.CrewRecruitState;
import com.pencilwith.apiserver.domain.repository.CrewRecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CrewBatchService {

    final private CrewRecruitRepository crewRecruitRepository;

    @Scheduled(cron = "0 0 3 * * ?")
    public void checkCrewRecruit() {
        setFinishOnOutOfDate();
    }

    //15일 지난 CrewRecruit는 FINISH 상태로 전환
    @Transactional
    public void setFinishOnOutOfDate() {
        LocalDateTime baseDay = LocalDateTime.now().minusDays(15);
        List<CrewRecruit> target = this.crewRecruitRepository.findByCreatedAtBeforeAndState(baseDay, CrewRecruitState.POST);
        target.stream().forEach(crewRecruit -> {
            crewRecruit.setState(CrewRecruitState.FINISH);
        });
    }

}
