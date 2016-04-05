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
import name.alexkosarev.bootdesk.entities.TicketComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketCommentRepository extends CrudRepository<TicketComment, Long> {

    List<TicketComment> findByTicket(Ticket ticket);

    @Query(name = "select tc from TicketComment tc join ticket t where t.id = :id")
    List<TicketComment> findByTicketId(@Param("ticketId") long id);
}
