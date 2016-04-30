/* eslint-disable react/prop-types */

import React from 'react';
import { Route, IndexRoute } from 'react-router';

import CobaseAppContainer from './components/container/CobaseAppContainer';
import LoggedInContainer from './components/container/LoggedInContainer';
import LoggedOutContainer from './components/container/LoggedOutContainer';
import AddGroupContainer from './components/container/AddGroupContainer';
import GroupContainer from './components/container/GroupContainer';
import Dashboard from './components/Dashboard';
import Login from './components/Login';
import { initUser } from './actions/UserActions';

export function createRoutes({ store }) {
  function initApp(nextState, replace, callback) {
    initUser()(store.dispatch);

    callback();
  }

  function requiresLogin(nextState, replace) {
    const user = store.getState().authentication.currentUser.user;

    if (!user) {
      replace(
        {
          next: nextState.location.pathname,
        },
        '/'
      );
    }
  }

  return (
    <Route path="/" component={CobaseAppContainer} onEnter={initApp}>
      <Route component={LoggedInContainer} onEnter={requiresLogin}>
        <Route path="dashboard" component={Dashboard} />
        <Route path="groups/add" component={AddGroupContainer} />
        <Route path="group/:groupId" component={GroupContainer} />
      </Route>

      <Route component={LoggedOutContainer}>
        <IndexRoute component={Login} />
      </Route>
    </Route>
  );
}
