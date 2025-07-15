package com.example.spirng.repository;

import com.example.spirng.domain.Member;

import java.util.*;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 * 리포지토리를 인터페이스로 추상화하면, 실제 데이터 저장 기술(DB, JPA 등)이 나중에 결정되어도 인터페이스를 구현하는 다른 클래스로 쉽게 교체할 수 있습니다. 초기엔 메모리 구현체를 활용하기도 합니다.
 */
public class MemoryMemberRepository implements MemberRepository{
        private static Map<Long, Member> store = new HashMap<>();
        private static long sequence = 0L;
        @Override
        public Member save(Member member) {
            member.setId(++sequence);
            store.put(member.getId(), member);
            return member;
        }
        @Override
        public Optional<Member> findById(Long id) {
            return Optional.ofNullable(store.get(id));
        }
        @Override
        public List<Member> findAll() {
            return new ArrayList<>(store.values());
        }
        @Override
        public Optional<Member> findByName(String name) {
            return store.values().stream()
                    .filter(member -> member.getName().equals(name))
                    .findAny();
        }
        public void clearStore() {
            store.clear();
        }


}
