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

const isFetchingCurrentUser = handleActions({
  LOGIN_REQUEST_SUCCESS: (state, action) => {
    return false;
  },

  LOGIN_REQUEST_FAILURE: (state, action) => {
    return false;
  },

  LOGIN_REQUEST: (state, action) => {
    return true;
  },

  LOGOUT_REQUEST_SUCCESS: (state, action) => {
    return false;
  },

  LOGOUT_REQUEST_FAILURE: (state, action) => {
    return false;
  },

  LOGOUT_REQUEST: (state, action) => {
    return true;
  }
}, false);

export default combineReducers({
  currentUser: combineReducers({
    isFetching: isFetchingCurrentUser,
    user: user
  })
});
