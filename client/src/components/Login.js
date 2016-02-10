import React, { Component } from 'react';
import Topbar from './Topbar';
import LoginForm from './LoginForm';

export default class Login extends Component {
  render() {
    const { onLogin } = this.props;

    return (
      <div className="main">
        <Topbar title="Login" />

        <LoginForm onLogin={onLogin} />
      </div>
    );
  }
}
