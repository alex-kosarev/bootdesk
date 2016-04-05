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
package name.alexkosarev.bootdesk.controllers;

import java.util.Date;
import name.alexkosarev.bootdesk.entities.TicketComment;
import name.alexkosarev.bootdesk.repositories.TicketCommentRepository;
import name.alexkosarev.bootdesk.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ticket/{ticketId:\\d+}/ticketComment")
public class TicketCommentController {

    @Autowired
    private TicketCommentRepository ticketCommentRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(@PathVariable long ticketId, @ModelAttribute TicketComment ticketComment) {
        ticketComment.setTicket(ticketRepository.findOne(ticketId));
        ticketComment.setCreateDate(new Date());
        ticketCommentRepository.save(ticketComment);

        return new ModelAndView("redirect:/ticket/" + ticketId + "/view");
    }
}
