package com.nhnacademy.miniDooray.dto.project;

import lombok.Data;

import java.util.List;

@Data
public class InviteMemberRequest {
    List<Long> userIds;
}
