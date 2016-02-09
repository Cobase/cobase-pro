import { createStore as reduxCreateStore, applyMiddleware, combineReducers } from 'redux';
import thunk from 'redux-thunk';
import { syncHistory, routeReducer } from 'react-router-redux';
import { createHistory } from 'history';
import promiseMiddleware from './promiseMiddleware';

export function createStore(reducers, history) {
  const createStoreWithMiddleware = applyMiddleware(
    thunk,
    promiseMiddleware,
    syncHistory(history)
  )(reduxCreateStore);

  const reducer = combineReducers({
    ...reducers,
    routing: routeReducer
  });

  return createStoreWithMiddleware(reducer);
}
