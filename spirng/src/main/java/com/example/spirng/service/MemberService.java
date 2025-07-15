package com.example.spirng.service;

import com.example.spirng.domain.Member;
import com.example.spirng.repository.MemberRepository;
import com.example.spirng.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository; //di -> 외부에서 memberRepository주입
        /*
         * 의존성 주입(DI)은 객체가 필요한 다른 객체(의존성)를 직접 생성하지 않고 외부에서 받아 사용하는 방식입니다. 코드의 재사용성을 높이고 테스트하기 쉽게 만들어 줍니다.
        * */
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) {
        //1.
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });
        //2.
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });

        // MemberRepository에 Optional로 선언되어 있어 2번처럼 선언해도 됨!
    }
    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
