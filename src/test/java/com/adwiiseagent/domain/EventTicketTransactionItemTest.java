package com.adwiiseagent.domain;

import static com.adwiiseagent.domain.EventTicketTransactionItemTestSamples.*;
import static com.adwiiseagent.domain.EventTicketTransactionTestSamples.*;
import static com.adwiiseagent.domain.EventTicketTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.adwiiseagent.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventTicketTransactionItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventTicketTransactionItem.class);
        EventTicketTransactionItem eventTicketTransactionItem1 = getEventTicketTransactionItemSample1();
        EventTicketTransactionItem eventTicketTransactionItem2 = new EventTicketTransactionItem();
        assertThat(eventTicketTransactionItem1).isNotEqualTo(eventTicketTransactionItem2);

        eventTicketTransactionItem2.setId(eventTicketTransactionItem1.getId());
        assertThat(eventTicketTransactionItem1).isEqualTo(eventTicketTransactionItem2);

        eventTicketTransactionItem2 = getEventTicketTransactionItemSample2();
        assertThat(eventTicketTransactionItem1).isNotEqualTo(eventTicketTransactionItem2);
    }
    /* @Test
    void transactionTest() throws Exception {
        EventTicketTransactionItem eventTicketTransactionItem = getEventTicketTransactionItemRandomSampleGenerator();
        EventTicketTransaction eventTicketTransactionBack = getEventTicketTransactionRandomSampleGenerator();

        eventTicketTransactionItem.setTransaction(eventTicketTransactionBack);
        assertThat(eventTicketTransactionItem.getTransaction()).isEqualTo(eventTicketTransactionBack);

        eventTicketTransactionItem.transaction(null);
        assertThat(eventTicketTransactionItem.getTransaction()).isNull();
    }

    @Test
    void ticketTypeTest() throws Exception {
        EventTicketTransactionItem eventTicketTransactionItem = getEventTicketTransactionItemRandomSampleGenerator();
        EventTicketType eventTicketTypeBack = getEventTicketTypeRandomSampleGenerator();

        eventTicketTransactionItem.setTicketType(eventTicketTypeBack);
        assertThat(eventTicketTransactionItem.getTicketType()).isEqualTo(eventTicketTypeBack);

        eventTicketTransactionItem.ticketType(null);
        assertThat(eventTicketTransactionItem.getTicketType()).isNull();
    }*/
}
