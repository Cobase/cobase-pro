import api from '../util/api';

export function login(username, password) {
  return {
    type: 'LOG_IN',
    promise: api.login({username, password})
  }
};
