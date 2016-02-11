import React from 'react';
import { Route, IndexRoute } from 'react-router';

import CobaseAppContainer from './components/container/CobaseAppContainer';
import LoggedInContainer from './components/container/LoggedInContainer';
import LoggedOutContainer from './components/container/LoggedOutContainer';
import Dashboard from './components/Dashboard';
import Login from './components/Login';
import { initUser } from './actions/UserActions';

export function createRoutes({ store, history }) {
  function initApp(nextState, replace, callback) {
    initUser()(store.dispatch);

    callback();
  }

  function requiresLogin(nextState, replace) {
    const user = store.getState().authentication.currentUser.user;

    if (!user) {
      replace(
        {
          next: nextState.location.pathname
        },
        '/'
      );
    }
  }

  return (
    <Route path="/" component={CobaseAppContainer} onEnter={initApp}>
      <Route component={LoggedInContainer} onEnter={requiresLogin}>
        <Route path="dashboard" component={Dashboard} />
      </Route>

      <Route component={LoggedOutContainer}>
        <IndexRoute component={Login} />
      </Route>
    </Route>
  );
}
