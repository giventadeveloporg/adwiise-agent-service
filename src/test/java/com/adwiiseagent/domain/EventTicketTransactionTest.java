package com.adwiiseagent.domain;

import static com.adwiiseagent.domain.EventDetailsTestSamples.*;
import static com.adwiiseagent.domain.EventTicketTransactionTestSamples.*;
import static com.adwiiseagent.domain.EventTicketTypeTestSamples.*;
import static com.adwiiseagent.domain.UserProfileTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.adwiiseagent.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventTicketTransactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventTicketTransaction.class);
        EventTicketTransaction eventTicketTransaction1 = getEventTicketTransactionSample1();
        EventTicketTransaction eventTicketTransaction2 = new EventTicketTransaction();
        assertThat(eventTicketTransaction1).isNotEqualTo(eventTicketTransaction2);

        eventTicketTransaction2.setId(eventTicketTransaction1.getId());
        assertThat(eventTicketTransaction1).isEqualTo(eventTicketTransaction2);

        eventTicketTransaction2 = getEventTicketTransactionSample2();
        assertThat(eventTicketTransaction1).isNotEqualTo(eventTicketTransaction2);
    }

    /*  @Test
    void eventTest() throws Exception {
        EventTicketTransaction eventTicketTransaction = getEventTicketTransactionRandomSampleGenerator();
        EventDetails eventDetailsBack = getEventDetailsRandomSampleGenerator();

        eventTicketTransaction.setEvent(eventDetailsBack);
        assertThat(eventTicketTransaction.getEvent()).isEqualTo(eventDetailsBack);

        eventTicketTransaction.event(null);
        assertThat(eventTicketTransaction.getEvent()).isNull();
    }*/

    @Test
    void ticketTypeTest() throws Exception {
        EventTicketTransaction eventTicketTransaction = getEventTicketTransactionRandomSampleGenerator();
        EventTicketType eventTicketTypeBack = getEventTicketTypeRandomSampleGenerator();
        /* eventTicketTransaction.setTicketType(eventTicketTypeBack);
        assertThat(eventTicketTransaction.getTicketType()).isEqualTo(eventTicketTypeBack);

        eventTicketTransaction.ticketType(null);
        assertThat(eventTicketTransaction.getTicketType()).isNull();*/
    }
    /* @Test
    void userTest() throws Exception {
        EventTicketTransaction eventTicketTransaction = getEventTicketTransactionRandomSampleGenerator();
        UserProfile userProfileBack = getUserProfileRandomSampleGenerator();

        eventTicketTransaction.setUser(userProfileBack);
        assertThat(eventTicketTransaction.getUser()).isEqualTo(userProfileBack);

        eventTicketTransaction.user(null);
        assertThat(eventTicketTransaction.getUser()).isNull();
    }*/
}
