package com.vaadin.starter.bakery.ui.views.admin.audit;

import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.starter.bakery.backend.data.AuditLogItem;
import com.vaadin.starter.bakery.backend.service.AuditLogService;
import com.vaadin.starter.bakery.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AuditPresenter {
    private UserService userService;
    private AuditView view;
    private final ListDataProvider<AuditLogItem> dataProvider;

    @Autowired
    public AuditPresenter(UserService userService){
        this.userService = userService;
        dataProvider = DataProvider.ofCollection(AuditLogService.getAuditLogItems());
    }

    void setView(AuditView view){
        this.view = view;

    }

    void populateUserFilter() {
        Collection<String> userNames = userService.getRepository().findAll().stream().map(user->user.getEmail()).collect(Collectors.toList());
        view.populateUserNames(userNames);
    }

    void filterLogItems(String userNameFilter) {
        dataProvider.clearFilters();
        Optional.ofNullable(userNameFilter).ifPresent(userName ->dataProvider.setFilter(item->userNameFilter.equals(item.getUserName())));
    }

    void populateLogItems() {
        view.populateLogItems(dataProvider);
    }
}
