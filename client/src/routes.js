import React from 'react';
import { Route, IndexRoute } from 'react-router';

import CobaseApp from './components/container/CobaseAppContainer';
import Dashboard from './components/Dashboard';
import Login from './components/Login';

export function createRoutes({ store, history }) {
  return (
    <Route path="/" component={CobaseApp}>
      <IndexRoute component={Dashboard} />
      <Route path="login" component={Login} />
    </Route>
  );
}
