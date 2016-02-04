import React from 'react';
import { Provider } from 'react-redux';
import { Router } from 'react-router';

export function createApp(store, history, routes) {
  return (
    <Provider store={store}>
      <Router history={history}>
        {routes}
      </Router>
    </Provider>
  );
}
