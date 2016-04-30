import React, { PropTypes } from 'react';
import Topbar from './Topbar';
import LoginForm from './LoginForm';

const Login = ({ onLogin }) => (
  <div className="main">
    <Topbar title="Login" />

    <div className="login-container">
      <section className="login">
        <LoginForm onLogin={onLogin} />
      </section>
    </div>
  </div>
);

Login.propTypes = {
  onLogin: PropTypes.func,
};

export default Login;
