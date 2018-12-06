package web.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import web.model.Result;

import java.util.UUID;

@Repository
public interface ResultRepository extends PagingAndSortingRepository<Result, UUID> {

}
