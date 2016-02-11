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
  }
}
