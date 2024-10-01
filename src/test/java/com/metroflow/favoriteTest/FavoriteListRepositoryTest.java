package com.metroflow.favoriteTest;

import com.metroflow.model.dto.FavoriteList;
import com.metroflow.model.dto.SubwayStation;
import com.metroflow.model.dto.User;
import com.metroflow.repository.FavoriteListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FavoriteListRepositoryTest {

    @Autowired
    private FavoriteListRepository favoriteListRepository;

    private User user;
    private SubwayStation subwayStation;

    @BeforeEach
    public void setup() {
        // 객체 초기화
        user = new User();
        user.setUserId("1111");
        user.setNickname("test");
        user.setUserEmail("ksj@naver.com");
        user.setPassword("1111");
        user.setUserImgPath("");

        Set<String> roles = new HashSet<>();
        roles.add("user");
        user.setUserRole(roles);

        subwayStation = new SubwayStation();
        subwayStation.setStationId(76);
        subwayStation.setStationLine("3");
        subwayStation.setStationName("약수");

        // FavoriteList 객체 생성 및 저장
        FavoriteList favoriteList = new FavoriteList();
        favoriteList.setUser(user);
        favoriteList.setStation(subwayStation);
        favoriteList.setMyfavNo(1);

        // 리포지토리에 저장
        favoriteListRepository.save(favoriteList);
    }

    @Test
    public void testIsFavorite_Returns1_WhenFavoriteExists() {
        Integer result = favoriteListRepository.isFavorite("1111", "약수"); // userId를 "1111"로 수정
        assertThat(result).isEqualTo(1);
    }
}
