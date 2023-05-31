package com.oslab.cmanager.containerManagingBean;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
@RequiredArgsConstructor
@Component
@Data
public class cManagingBean {

    /* Container UUID Run Queue */
    private Queue<String> runningQueue = new LinkedList<>();

    /* Container UUID Waiting Queue */
    private Queue<String> waitingQueue = new LinkedList<>();


}
