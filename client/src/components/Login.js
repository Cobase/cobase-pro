import React, { Component } from 'react';

export default class Login extends Component {
  render() {
    return (
      <div className="">
        <h1>Login</h1>
        <form>
          <input type="text" value="user" placeholder="User" />
          <input type="password" value="user" placeholder="Password" />
          <button type="submit">Login</button>
        </form>
      </div>
    );
  }
}
