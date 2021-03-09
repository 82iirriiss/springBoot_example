package com.example.ex03.guestbook.repository;

import com.example.ex03.entity.Guestbook;
import com.example.ex03.entity.QGuestbook;
import com.example.ex03.repository.GuestbookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository repository;

    @Test
    public void insertDummies(){

        IntStream.rangeClosed(1, 300).forEach(i ->{
            Guestbook guestbook = Guestbook.builder()
                                    .title("Title....." + i)
                                    .content("Content...."+i)
                                    .writer("user" + i)
                                    .build();
            System.out.println(repository.save(guestbook));
        });
    }


    @Test
    public void testUpdate(){
        Optional<Guestbook> result = repository.findById(300L);

        if(result.isPresent()){
            Guestbook guestbook = result.get();

            guestbook.changeTitle("Change Title...");
            guestbook.changeContent("Change Content...");

            repository.save(guestbook);

        }
    }


    @Test
    public void testQuery1(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qGuestbook.title.contains(keyword);
        builder.and(expression);

        Page<Guestbook> result = repository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }


    @Test
    public void testQuery2(){

        String keyword = "1";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression title = qGuestbook.title.contains(keyword);
        BooleanExpression content = qGuestbook.content.contains(keyword);
        BooleanExpression writer = qGuestbook.writer.contains(keyword);
        BooleanExpression condition = title.or(content).or(writer);

        builder.and(qGuestbook.gno.gt(0L));
        builder.and(condition);
        Page<Guestbook> result = repository.findAll(builder, pageable);

        result.stream().forEach(guestbook->{
            System.out.println(guestbook);
        });
    }
}
