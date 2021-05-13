package com.pencilwith.apiserver.crew;

import com.pencilwith.apiserver.domain.entity.CrewRecruit;
import com.pencilwith.apiserver.domain.entity.CrewRecruitState;
import com.pencilwith.apiserver.domain.repository.CrewRecruitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class CrewBatchServiceTest {

    @Autowired
    CrewRecruitRepository crewRecruitRepository;

    @Autowired
    CrewBatchService crewBatchService;

    @Test
    void setFinishOnOutOfDate() {
        CrewRecruit c1 = new CrewRecruit();
        c1.setCreatedAt(LocalDateTime.now());
        c1.setState(CrewRecruitState.POST);
        c1 = this.crewRecruitRepository.save(c1);

        CrewRecruit c2 = new CrewRecruit();
        c2.setCreatedAt(LocalDateTime.now().minusDays(16));
        c2.setState(CrewRecruitState.POST);
        c2 = this.crewRecruitRepository.save(c2);

        assertThat(c1.getState()).isEqualTo(CrewRecruitState.POST);
        assertThat(c2.getState()).isEqualTo(CrewRecruitState.POST);

        this.crewBatchService.setFinishOnOutOfDate();

        c1 = this.crewRecruitRepository.findById(c1.getId()).get();
        c2 = this.crewRecruitRepository.findById(c2.getId()).get();

        assertThat(c1.getState()).isEqualTo(CrewRecruitState.POST);
        assertThat(c2.getState()).isEqualTo(CrewRecruitState.FINISH);

    }
}