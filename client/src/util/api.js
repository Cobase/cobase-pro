import axios from 'axios';
import config from '../../conf/config';

function authConfig(token) {
  return {
    headers: {
      'X-Token': token
    }
  };
}

export default {
  login(username, password) {
    return axios.post(
      `${config.apiUrl}/login`,
      {
        username,
        password
      }
    );
  },

  logout(user) {
    return axios.post(
      `${config.apiUrl}/logout`,
      {},
      authConfig(user.token)
    );
  },

  addGroup(user, title, tags) {
    return axios.post(
      `${config.apiUrl}/groups`,
      {
        title,
        tags
      },
      authConfig(user.token)
    );
  },

  getGroups(user) {
    return axios.get(
      `${config.apiUrl}/groups`,
      authConfig(user.token)
    );
  }
}
