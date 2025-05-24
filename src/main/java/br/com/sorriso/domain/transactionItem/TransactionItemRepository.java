package br.com.sorriso.domain.transactionItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionItemRepository extends JpaRepository<TransactionItem, UUID> {
}
