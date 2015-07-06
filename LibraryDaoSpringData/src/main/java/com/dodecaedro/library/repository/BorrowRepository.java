package com.dodecaedro.library.repository;

import com.dodecaedro.library.data.pojo.Borrow;
import com.dodecaedro.library.data.pojo.BorrowId;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = Borrow.class, idClass = BorrowId.class)
public interface BorrowRepository {
  Borrow save(Borrow borrow);
  List<Borrow> findAll();
}
