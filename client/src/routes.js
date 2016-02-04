import React from 'react';
import { Route, IndexRoute } from 'react-router';

import CobaseApp from './components/container/CobaseAppContainer';
import IndexPage from './components/IndexPage';

export function createRoutes({ store, history }) {
  return (
    <Route path="/" component={CobaseApp}>
      <IndexRoute component={IndexPage} />
    </Route>
  );
}
