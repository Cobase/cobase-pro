import axios from 'axios';
import config from '../../conf/config';

const api = axios.create({
  baseURL: config.apiUrl
});

const authConfig = token => ({
  headers: {
    'X-Token': token
  }
});

export default {
  login(username, password) {
    return api.post(
      '/login',
      {
        username,
        password
      }
    );
  },

  logout({token}) {
    return api.post(
      '/logout',
      {},
      authConfig(token)
    );
  },

  addGroup({token}, title, tags) {
    return api.post(
      '/groups',
      {
        title,
        tags
      },
      authConfig(token)
    );
  },

  getGroups({token}) {
    return api.get(
      '/groups',
      authConfig(token)
    );
  }
}
