package deors.demos.cloud.appengine.client.entrypoints;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;

import deors.demos.cloud.appengine.client.services.AuthInfoService;
import deors.demos.cloud.appengine.client.services.AuthInfoServiceAsync;
import deors.demos.cloud.appengine.client.services.ThoughtService;
import deors.demos.cloud.appengine.client.services.ThoughtServiceAsync;
import deors.demos.cloud.appengine.shared.AuthInfo;
import deors.demos.cloud.appengine.shared.Thought;

public class ThinkingLoud
    implements EntryPoint {

    String fromUser = "anonymous";

    Date latest = new Date();

    VerticalPanel mainPanel = new VerticalPanel();
    Label headerLabel = new Label("Thinking Loud");
    Label messageLabel = new Label("What are you thinking?");

    HorizontalPanel messagePanel = new HorizontalPanel();
    TextBox messageField = new TextBox();
    Button postButton = new Button("Share it!");

    HorizontalPanel senderPanel = new HorizontalPanel();
    Label fromLabel = new Label("From: ");
    Label fromUserLabel = new Label(fromUser);
    Label toLabel = new Label("To:");
    TextBox toField = new TextBox();

    FlexTable thoughtsTable = new FlexTable();

    Anchor loginAnchor = new Anchor("Click here to login", "");
    Anchor logoutAnchor = new Anchor("Click here to logout", "");

    ThoughtServiceAsync thoughtService = GWT.create(ThoughtService.class);
    AuthInfoServiceAsync authInfoService = GWT.create(AuthInfoService.class);

    Timer refreshTimer = new Timer() {

        @Override
        public void run() {

            thoughtService.getThoughtsAfterDate(latest, new AsyncCallback<List<Thought>>() {

                @Override
                public void onSuccess(List<Thought> list) {

                    loadThoughtList(list);
                }

                @Override
                public void onFailure(Throwable caught) {

                    Window.alert("Error refreshing post list. Please try again.");
                }
            });
        }
    };

    @Override
    public void onModuleLoad() {

        mainPanel.add(headerLabel);
        mainPanel.add(messageLabel);
        mainPanel.add(messagePanel);
        mainPanel.add(senderPanel);
        mainPanel.add(thoughtsTable);
        mainPanel.add(loginAnchor);
        mainPanel.add(logoutAnchor);

        senderPanel.add(fromLabel);
        senderPanel.add(fromUserLabel);
        senderPanel.add(toLabel);
        senderPanel.add(toField);

        messagePanel.add(messageField);
        messagePanel.add(postButton);

        loginAnchor.setVisible(false);
        logoutAnchor.setVisible(false);

        headerLabel.addStyleName("headerLabel");
        messageLabel.addStyleName("messageLabel");
        fromLabel.addStyleName("fromLabel");
        fromUserLabel.addStyleName("fromLabel");
        toLabel.addStyleName("toLabel");
        toField.addStyleName("toField");
        messagePanel.addStyleName("messagePanel");
        messageField.addStyleName("messageField");
        postButton.addStyleName("postButton");
        thoughtsTable.addStyleName("thoughtsTable");
        loginAnchor.addStyleName("anchor");
        logoutAnchor.addStyleName("anchor");

        postButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                postThought();
            }
        });

        RootPanel rootPanel = RootPanel.get("wall");
        rootPanel.add(mainPanel);

        loadHistory();
        loadAuthInfo();
    }

    private void postThought() {

        Thought th = new Thought(
            fromUser,
            toField.getText(),
            messageField.getText(),
            new Date());

        thoughtService.postThought(th, latest, new AsyncCallback<List<Thought>>() {

            @Override
            public void onSuccess(List<Thought> list) {

                loadThoughtList(list);
            }

            @Override
            public void onFailure(Throwable caught) {

                Window.alert("Error posting your thought. Please try again.");
            }
        });
    }

    private void loadHistory() {

        thoughtService.getThoughts(new AsyncCallback<List<Thought>>() {

            @Override
            public void onSuccess(List<Thought> list) {

                loadThoughtList(list);

                refreshTimer.scheduleRepeating(2000);
            }

            @Override
            public void onFailure(Throwable caught) {

                Window.alert("Error getting post history. Please try again.");
            }
        });
    }

    private void loadAuthInfo() {

        String backUrl = Window.Location.getPath() + Window.Location.getQueryString();

        authInfoService.getAuthInfo(backUrl, new AsyncCallback<AuthInfo>() {

            @Override
            public void onSuccess(AuthInfo authInfo) {

                loginAnchor.setHref(authInfo.getLoginUrl());
                logoutAnchor.setHref(authInfo.getLogoutUrl());

                if (authInfo.isLogged()) {
                    fromUser = authInfo.getNick();
                    loginAnchor.setVisible(false);
                    logoutAnchor.setVisible(true);
                } else {
                    fromUser = "anonymous";
                    loginAnchor.setVisible(true);
                    logoutAnchor.setVisible(false);
                }

                fromUserLabel.setText(fromUser);

            }

            @Override
            public void onFailure(Throwable caught) {

                Window.alert("Error getting authentication information. Please try again.");
            }
        });
    }

    void loadThoughtList(List<Thought> list) {

        if (!list.isEmpty()) {
            for (Thought th : list) {
                thoughtsTable.insertRow(0);
                thoughtsTable.setText(0, 0, th.toString());
            }

            latest = list.get(list.size() - 1).getPostDate();
        }
    }
}
