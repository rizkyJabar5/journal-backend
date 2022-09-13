package com.journal.florist;

import com.journal.florist.backend.feature.ledger.model.Sales;
import com.journal.florist.backend.feature.ledger.repositories.SalesRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureTestDatabase
@RunWith(SpringRunner.class)
@DataJpaTest
public class SaleIntegrarionTest {

    @Autowired
    private SalesRepository repository;

    @Test
    public void whenFindByPublicationDate_thenArticles1And2Returned() throws ParseException {
        List<Sales> result = repository.findAllBySaleDate(
                new SimpleDateFormat("yyyy-MM-dd").parse("2018-01-01"));

        assertEquals(2, result.size());
        assertTrue(result.stream()
                .map(Sales::getId)
                .allMatch(id -> Arrays.asList(1, 2).contains(id)));
    }

//    @Test
//    public void whenFindByPublicationTimeBetween_thenArticles2And3Returned() {
//        List<Article> result = repository.findAllByPublicationTimeBetween(
//                new SimpleDateFormat("HH:mm").parse("15:15"),
//                new SimpleDateFormat("HH:mm").parse("16:30"));
//
//        assertEquals(2, result.size());
//        assertTrue(result.stream()
//                .map(Article::getId)
//                .allMatch(id -> Arrays.asList(2, 3).contains(id)));
//    }
//
//    @Test
//    public void givenArticlesWhenFindWithCreationDateThenArticles2And3Returned() {
//        List<Article> result = repository.findAllWithCreationDateTimeBefore(
//                new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2017-12-15 10:00"));
//
//        assertEquals(2, result.size());
//        assertTrue(result.stream()
//                .map(Article::getId)
//                .allMatch(id -> Arrays.asList(2, 3).contains(id));
//    }
}