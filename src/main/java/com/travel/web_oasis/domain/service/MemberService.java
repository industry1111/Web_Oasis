package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.repository.MemberRepository;
import com.travel.web_oasis.web.dto.MemberDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Long saveMember(MemberDTO memberDto) {

        Member member = dtoToEntity(memberDto);

        return memberRepository.save(member).getId();
    }

    public Member findById(Long id) {

        return memberRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("해당 멤버가 없습니다. id = " + id));

    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public void updateMember(Long id, MemberDTO memberDto) {

        Member member = memberRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        member.update(memberDto);

        memberRepository.save(member);

    }

    public Member dtoToEntity(MemberDTO memberDto) {
        return Member.builder()
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .phone(memberDto.getPhone())
                .role(memberDto.getRole())
                .status(memberDto.getStatus())
                .build();
    }

    public MemberDTO entityToDto(Member member) {
        return MemberDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .password(member.getPassword())
                .phone(member.getPhone())
                .role(member.getRole())
                .status(member.getStatus())
                .build();
    }


}