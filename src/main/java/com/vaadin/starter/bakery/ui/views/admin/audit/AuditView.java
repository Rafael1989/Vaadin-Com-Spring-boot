package com.vaadin.starter.bakery.ui.views.admin.audit;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.bakery.backend.data.AuditLogItem;
import com.vaadin.starter.bakery.backend.data.Role;
import com.vaadin.starter.bakery.backend.service.AuditLogService;
import com.vaadin.starter.bakery.backend.service.UserService;
import com.vaadin.starter.bakery.ui.MainView;
import com.vaadin.starter.bakery.ui.utils.BakeryConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Route(value = BakeryConst.PAGE_AUDIT, layout = MainView.class)
@PageTitle(BakeryConst.TITLE_PRODUCTS)
@Secured(Role.ADMIN)
public class AuditView extends Composite<VerticalLayout> implements HasComponents{

    private AuditPresenter presenter;
    private final ComboBox<String> userFilter;
    private final Grid<AuditLogItem> logItemsGrid;

    @Autowired
    public AuditView(AuditPresenter presenter){
        getContent().setSpacing(true);
        getContent().setMargin(true);
        this.presenter = presenter;
        presenter.setView(this);

        userFilter = new ComboBox<>();
        userFilter.setLabel("Filter by user name:");
        presenter.populateUserFilter();

        userFilter.addValueChangeListener(e->{
            presenter.filterLogItems(e.getValue());
        });

        add(userFilter);


        logItemsGrid = new Grid<>();
        logItemsGrid.addColumn(new LocalDateTimeRenderer<>(AuditLogItem::getLocalDateTime)).setHeader("Time");
        logItemsGrid.addColumn(AuditLogItem::getUserName).setHeader("User Name");
        logItemsGrid.addColumn(AuditLogItem::getMessage).setHeader("Message").setFlexGrow(3);
        presenter.populateLogItems();
        add(logItemsGrid);

        getContent().setFlexGrow(1, logItemsGrid);

    }

    void populateUserNames(Collection<String> userNames) {
        userFilter.setItems(userNames);
    }

    void populateLogItems(ListDataProvider<AuditLogItem> dataProvider) {
        logItemsGrid.setDataProvider(dataProvider);
    }
}
