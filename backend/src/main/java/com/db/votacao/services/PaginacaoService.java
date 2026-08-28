package com.db.votacao.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class PaginacaoService {

    @PersistenceContext
    protected EntityManager entityManager;

    protected Pageable criarPageable(int pagina, int tamanho, String campoOrdenacao, Sort.Direction direcao) {
        int paginaAtual = Math.max(pagina, 0);
        int tamanhoPagina = tamanho > 0 ? tamanho : 10;

        if (campoOrdenacao == null || campoOrdenacao.isBlank()) {
            return PageRequest.of(paginaAtual, tamanhoPagina);
        }

        Sort.Direction direcaoOrdenacao = direcao == null ? Sort.Direction.ASC : direcao;
        return PageRequest.of(paginaAtual, tamanhoPagina, Sort.by(direcaoOrdenacao, campoOrdenacao));
    }

    protected <E, D> Page<D> paginar(Class<E> entidade, int pagina, int tamanho, String campoOrdenacao, Sort.Direction direcao, Function<E, D> mapper) {
        Pageable pageable = criarPageable(pagina, tamanho, campoOrdenacao, direcao);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<E> countRoot = countQuery.from(entidade);
        countQuery.select(criteriaBuilder.count(countRoot));
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        CriteriaQuery<E> query = criteriaBuilder.createQuery(entidade);
        Root<E> root = query.from(entidade);
        query.select(root);

        if (pageable.getSort().isSorted()) {
            List<Order> orders = new ArrayList<>();
            for (Sort.Order order : pageable.getSort()) {
                if (order.isAscending()) {
                    orders.add(criteriaBuilder.asc(root.get(order.getProperty())));
                } else {
                    orders.add(criteriaBuilder.desc(root.get(order.getProperty())));
                }
            }
            query.orderBy(orders);
        }

        TypedQuery<E> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<E> conteudo = typedQuery.getResultList();
        List<D> itensMapeados = conteudo.stream().map(mapper).toList();
        return new PageImpl<>(itensMapeados, pageable, total);
    }
}
