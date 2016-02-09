import React, { Component } from 'react';
import Topbar from './Topbar';

export default class Login extends Component {
  render() {
    return (
      <div className="main">
        <Topbar title="Login" />
        <form>
          <input type="text" value="user" placeholder="User" />
          <input type="password" value="user" placeholder="Password" />
          <button type="submit">Login</button>
        </form>
      </div>
    );
  }
}
