import React, { Component } from 'react';
import Topbar from './Topbar';
import LoginForm from './LoginForm';

export default class Login extends Component {
  render() {
    const { onLogin } = this.props;

    return (
      <div className="main">
        <Topbar title="Login" />

        <div className="login-container">
          <section className="login">
            <LoginForm onLogin={onLogin} />
          </section>
        </div>
      </div>
    );
  }
}
