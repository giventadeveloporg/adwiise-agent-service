package com.adwiiseagent.domain;

import static com.adwiiseagent.domain.EventDetailsTestSamples.*;
import static com.adwiiseagent.domain.EventTicketTransactionTestSamples.*;
import static com.adwiiseagent.domain.UserPaymentTransactionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.adwiiseagent.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserPaymentTransactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPaymentTransaction.class);
        UserPaymentTransaction userPaymentTransaction1 = getUserPaymentTransactionSample1();
        UserPaymentTransaction userPaymentTransaction2 = new UserPaymentTransaction();
        assertThat(userPaymentTransaction1).isNotEqualTo(userPaymentTransaction2);

        userPaymentTransaction2.setId(userPaymentTransaction1.getId());
        assertThat(userPaymentTransaction1).isEqualTo(userPaymentTransaction2);

        userPaymentTransaction2 = getUserPaymentTransactionSample2();
        assertThat(userPaymentTransaction1).isNotEqualTo(userPaymentTransaction2);
    }

    @Test
    void eventTest() throws Exception {
        UserPaymentTransaction userPaymentTransaction = getUserPaymentTransactionRandomSampleGenerator();
        EventDetails eventDetailsBack = getEventDetailsRandomSampleGenerator();

        userPaymentTransaction.setEvent(eventDetailsBack);
        assertThat(userPaymentTransaction.getEvent()).isEqualTo(eventDetailsBack);

        userPaymentTransaction.event(null);
        assertThat(userPaymentTransaction.getEvent()).isNull();
    }

    @Test
    void ticketTransactionTest() throws Exception {
        UserPaymentTransaction userPaymentTransaction = getUserPaymentTransactionRandomSampleGenerator();
        EventTicketTransaction eventTicketTransactionBack = getEventTicketTransactionRandomSampleGenerator();

        userPaymentTransaction.setTicketTransaction(eventTicketTransactionBack);
        assertThat(userPaymentTransaction.getTicketTransaction()).isEqualTo(eventTicketTransactionBack);

        userPaymentTransaction.ticketTransaction(null);
        assertThat(userPaymentTransaction.getTicketTransaction()).isNull();
    }
}
