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
import name.alexkosarev.bootdesk.entities.Ticket;
import name.alexkosarev.bootdesk.repositories.TicketCommentRepository;
import name.alexkosarev.bootdesk.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketCommentRepository ticketCommentRepository;

    /**
     * Список заявок.
     *
     * @param all Отображать все заявки
     * @return
     */
    @RequestMapping({"", "/", "/index"})
    public ModelAndView index(@RequestParam(required = false, defaultValue = "false") boolean all) {
        return new ModelAndView("ticket/index")
                .addObject("all", all)
                .addObject("tickets", all
                        ? ticketRepository.findAll(new Sort(Sort.Direction.DESC, "createDate"))
                        : ticketRepository.findByResolveDateIsNull(new Sort(Sort.Direction.DESC, "createDate")));
    }

    /**
     * Создание новой заявки.
     *
     * @param ticket Заявка
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(@ModelAttribute Ticket ticket) {
        ticket.setCreateDate(new Date());
        ticket = ticketRepository.save(ticket);

        return new ModelAndView("redirect:/ticket/" + ticket.getId() + "/view");
    }

    /**
     * Просмотр заявки.
     *
     * @param id Идентификатор запрашиваемой заявки
     * @return
     */
    @RequestMapping("/{id:\\d+}/view")
    public ModelAndView view(@PathVariable long id) {
        Ticket ticket = ticketRepository.findOne(id);

        return new ModelAndView("ticket/view")
                .addObject("ticket", ticket)
                .addObject("ticketComments", ticketCommentRepository.findByTicketId(id));
    }

    /**
     * Закрытие заявки.
     *
     * @param id Идентификатор запрашиваемой заявки
     * @return
     */
    @RequestMapping("/{id:\\d+}/resolve")
    public ModelAndView resolve(@PathVariable long id) {
        ticketRepository.resolveTicket(id);

        return new ModelAndView("redirect:/ticket/" + id + "/view");
    }

    /**
     * Повторное открытие заявки.
     *
     * @param id Идентификатор запрашиваемой заявки
     * @return
     */
    @RequestMapping("/{id:\\d+}/reopen")
    public ModelAndView reopen(@PathVariable long id) {
        ticketRepository.reopenTicket(id);

        return new ModelAndView("redirect:/ticket/" + id + "/view");
    }
}
