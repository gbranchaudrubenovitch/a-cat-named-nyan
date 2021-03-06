package com.gbr.nyan.web;

import com.gbr.nyan.domain.Account;
import com.gbr.nyan.domain.AccountRepository;
import com.gbr.nyan.domain.User;
import com.gbr.nyan.domain.UserRepository;
import com.gbr.nyan.support.HandlebarsRenderer;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gbr.nyan.web.support.SecurityContextHelper.userLoggedIn;
import static com.gbr.nyan.web.support.SecurityContextHelper.userNotLoggedIn;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class RepoListControllerTest {
    private RepoListController controller;
    private HandlebarsRenderer viewRenderer;
    private UserRepository userRepository;
    private AccountRepository accountRepository;

    @Before
    public void thisController() throws Exception {
        userNotLoggedIn();

        viewRenderer = mock(HandlebarsRenderer.class);
        userRepository = mock(UserRepository.class);
        accountRepository = mock(AccountRepository.class);

        when(accountRepository.save(any(Account.class))).thenReturn(new Account());
        when(userRepository.findAll()).thenReturn(singletonList(someUser("3@3.com")));
        when(viewRenderer.render(anyString(), any())).thenReturn("the body");

        controller = new RepoListController(viewRenderer, userRepository, accountRepository);
    }

    @Test
    public void passesTheUsersToTheViewRenderer() throws Exception {
        List<User> theUsers = asList(someUser("1@1.com"), someUser("2@2.com"));
        when(userRepository.findAll()).thenReturn(theUsers);

        controller.listRepos(empty(), empty());

        Map<String, Object> expectedContext = new HashMap<>();
        expectedContext.put("page-title", "A cat named Nyan - Content of the repositories");
        expectedContext.put("user-is-logged-in", false);
        expectedContext.put("user-can-be-upgraded", false);
        expectedContext.put("rendering-repo-list", true);
        expectedContext.put("users", theUsers);

        verify(viewRenderer).render("/templates/repo-list", expectedContext);
    }

    @Test
    public void returnsTheViewResults() throws Exception {
        String responseBody = controller.listRepos(empty(), empty());

        assertThat(responseBody, is("the body"));
    }

    @Test
    public void hackishlyAddsUserWhenParamIsSet() throws Exception {
        controller.listRepos(of("yes-do-create-a-user"), empty());

        verify(accountRepository).save(any(Account.class));
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void hackishlyUpgradesYouWhenParamIsSet() throws Exception {
        userLoggedIn();

        controller.listRepos(empty(), of("upgrade-me"));

        verify(accountRepository).save(any(Account.class));
        verify(userRepository).save(any(User.class));
    }

    private User someUser(String email) {
        User user = new User();
        user.setEmail(email);
        return user;
    }
}