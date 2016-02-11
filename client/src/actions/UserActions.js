import api from '../util/api';
import { push } from 'react-router-redux';

function loginRequest(username, password) {
  return {
    type: 'LOGIN_REQUEST',
    username,
    password
  };
}

function loginRequestSuccess(response) {
  return {
    type: 'LOGIN_REQUEST_SUCCESS',
    response
  };
}

function loginRequestFailure(error) {
  return {
    type: 'LOGIN_REQUEST_FAILURE',
    error
  };
}

export function login(username, password) {
  return (dispatch) => {
    dispatch(loginRequest(username, password));

    api.login(username, password)
      .then(response => {
        dispatch(loginRequestSuccess(response));

        window.localStorage.setItem('currentUser', JSON.stringify(response.data));

        dispatch(push('/dashboard', {}));
      })
      .catch(e => {
        dispatch(loginRequestFailure(e));
      })
  };
}

function logoutRequest() {
  return {
    type: 'LOGOUT_REQUEST'
  };
}

function logoutRequestSuccess() {
  return {
    type: 'LOGOUT_REQUEST_SUCCESS'
  };
}

function logoutRequestFailure() {
  return {
    type: 'LOGOUT_REQUEST_FAILURE'
  };
}

export function logout(user) {
  return (dispatch) => {
    dispatch(logoutRequest());

    const doLogout = () => {
      dispatch(logoutRequestSuccess());

      window.localStorage.removeItem('currentUser');

      dispatch(push('/', {}));
    };

    return api.logout(user)
      .then(response => doLogout())
      .catch(e => {
        if (e.status === 401) {
          doLogout();
        } else {
          return dispatch(logoutRequestFailure(e));
        }
      });
  };
}

function getUserFromCache(user) {
  return {
    type: 'GET_USER_FROM_CACHE',
    user
  };
}

export function initUser() {
  return (dispatch) => {
    const currentUser = window.localStorage.getItem('currentUser');

    if (currentUser) {
      dispatch(getUserFromCache(JSON.parse(currentUser)));
    }
  };
}
