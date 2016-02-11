import { handleActions } from 'redux-actions';
import { combineReducers } from 'redux';
import { List, Map, Record } from 'immutable';

const User = Record({
  id: null,
  username: null,
  role: null,
  token: null
});

const user = handleActions({
  LOGIN_REQUEST_SUCCESS: (state, action) => {
    const { id, username, role, token } = action.response.data;

    return new User({
      id,
      username,
      role,
      token
    });
  },

  GET_USER_FROM_CACHE: (state, action) => {
    return new User(action.user);
  },

  LOGOUT_REQUEST_SUCCESS: (state, action) => {
    return null;
  }
}, null);

function isFetching(state = false, action) {
  switch (action.type) {
    case 'LOGIN_REQUEST':
    case 'LOGOUT_REQUEST':
      return true;

    case 'LOGIN_REQUEST_SUCCESS':
    case 'LOGIN_REQUEST_FAILURE':
    case 'LOGOUT_REQUEST_SUCCESS':
    case 'LOGOUT_REQUEST_FAILURE':
      return false;

    default:
      return state;
  }
}

export default combineReducers({
  currentUser: combineReducers({
    isFetching,
    user
  })
});
