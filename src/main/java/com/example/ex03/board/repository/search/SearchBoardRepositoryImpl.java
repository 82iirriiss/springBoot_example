package com.example.ex03.board.repository.search;

import com.example.ex03.board.entity.Board;
import com.example.ex03.board.entity.QBoard;
import com.example.ex03.board.entity.QMember;
import com.example.ex03.board.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search1() {

        log.info("search1.........");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count()).groupBy(board);

        log.info("------------------------------------");
        log.info(tuple);
        log.info("----------------------------------------");

        List<Tuple> result = tuple.fetch();
        log.info(result);
        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        log.info("searchPage..........................");

        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);

        builder.and(expression);
        if(type!= null) {
            BooleanBuilder condition = new BooleanBuilder();

            if (type.contains("t")) {
                condition.or(board.title.contains(keyword));
            }

            if (type.contains("c")) {
                condition.or(board.content.contains(keyword));
            }

            if (type.contains("w")) {
                condition.or(member.email.contains(keyword));
            }

            builder.and(condition);
        }

        tuple.where(builder);

        //pageable의 Sort를 Querydsl에서 사용할 수 있는 order by로 변환하는 로직.
        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Board.class, "board");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        tuple.groupBy(board);

        //페이지 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        log.info(result);
        long count = tuple.fetchCount();
        log.info("Count:" + count);

        return new PageImpl<Object[]>(
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
                pageable,
                count
        );
    }
}
