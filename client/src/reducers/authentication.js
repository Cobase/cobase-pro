import { combineReducers } from 'redux';
import { Record } from 'immutable';

const User = Record({
  id: null,
  username: null,
  displayname: null,
  role: null,
  token: null,
});

function user(state = null, action) {
  switch (action.type) {
    case 'LOGIN_REQUEST_SUCCESS':
      const {
        id,
        username,
        displayname,
        role,
        token,
      } = action.response.data;

      return new User({
        id,
        username,
        displayname,
        role,
        token,
      });

    case 'GET_USER_FROM_CACHE':
      return new User(action.user);

    case 'LOGOUT_REQUEST_SUCCESS':
      return null;

    default:
      return state;
  }
}

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
    user,
  }),
});
