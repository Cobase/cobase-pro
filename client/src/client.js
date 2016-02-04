import React from 'react';
import ReactDOM from 'react-dom';
import { browserHistory } from 'react-router';
import { createStore } from './util/redux';
import { createApp } from './util/app';

import * as reducers from './reducers';
import { createRoutes } from './routes';

const history = browserHistory;
const store = createStore(reducers, history);

const routes = createRoutes({
  store,
  history
});

const app = createApp(store, history, routes);

ReactDOM.render(
  app,
  document.getElementById('cobase')
);
