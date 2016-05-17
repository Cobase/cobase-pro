import { createStore as reduxCreateStore, applyMiddleware, combineReducers } from 'redux';
import thunk from 'redux-thunk';
import { syncHistory, routeReducer } from 'react-router-redux';
import createLogger from 'redux-logger';

export function createStore(reducers, history) {
  const logger = createLogger();

  const createStoreWithMiddleware = applyMiddleware(
    thunk,
    syncHistory(history),
    logger,
  )(reduxCreateStore);

  const reducer = combineReducers({
    ...reducers,
    routing: routeReducer,
  });

  return createStoreWithMiddleware(reducer);
}
