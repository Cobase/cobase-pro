import React from 'react';
import { Route, IndexRoute } from 'react-router';

import CobaseApp from './components/container/CobaseAppContainer';
import Dashboard from './components/Dashboard';

export function createRoutes({ store, history }) {
  return (
    <Route path="/" component={CobaseApp}>
      <IndexRoute component={Dashboard} />
    </Route>
  );
}
