package com.example.spirng.service;

import com.example.spirng.domain.Member;
import com.example.spirng.repository.MemberRepository;

import com.example.spirng.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    // 커맨드 + shift + t => 자동 테스트 케이스 생성 단축키
    // control + R => 이전 테스트 재 실행
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member  member = new Member();
        member.setName("testName");
        //when 검증
        Long saveId = memberService.join(member);
        //then 결과
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복회원_예외_발생(){
        //given
        Member  member1 = new Member();
        member1.setName("testName");

        Member  member2 = new Member();
        member2.setName("testName");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2)); //memberService join에 member2넣으면 IllegalStateException 발생한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

//        try {
//            memberService.join(member2);
//            fail("중복 예왼 발생 x"); //test 실패
//        }catch (IllegalStateException e){
//            //성공
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}