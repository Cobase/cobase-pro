import { handleActions } from 'redux-actions';
import { List, Map } from 'immutable';

const defaultState = Map({
  isLoggedIn: false,
  username: null,
  role: null,
  errorMessage: null
});

export default handleActions({

  LOG_IN: (state, action) => {
    console.log('login succeed');
    return state
      .set('isLoggedIn', true)
      .set('username', action.username)
      .set('role', action.role);
  },

  'LOG_IN-failed': (state, action) => {
    console.log('login failed');
    return state;
  }

}, defaultState);

/*
export default function(state = defaultState, action) {
  switch (action.type) {
    case
    default:
      return state;
  }
};
*/
