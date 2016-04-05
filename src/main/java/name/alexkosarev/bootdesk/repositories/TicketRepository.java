/*
 * Copyright 2016 Alexander Kosarev akosarev@acruxsource.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package name.alexkosarev.bootdesk.repositories;

import java.util.List;
import name.alexkosarev.bootdesk.entities.Ticket;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

    List<Ticket> findAll(Sort sort);

    List<Ticket> findByResolveDateIsNull(Sort sort);

    @Transactional
    @Modifying
    @Query("update Ticket set resolveDate = now() where id = :id")
    int resolveTicket(@Param("id") long id);

    @Transactional
    @Modifying
    @Query("update Ticket set resolveDate = null where id = :id")
    int reopenTicket(@Param("id") long id);
}
