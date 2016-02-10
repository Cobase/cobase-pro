import React, { Component, PropTypes } from 'react';

class LoginForm extends Component {
  render() {
    return (
      <form onSubmit={this.handleSubmit.bind(this)}>
        <input type="text" ref="username" placeholder="Username" />
        <input type="password" ref="password" placeholder="Password" />
        <button type="submit">Login</button>
      </form>
    );
  }

  handleSubmit(e) {
    e.preventDefault();

    this.props.onLogin(
      this.refs.username.value,
      this.refs.password.value
    );
  }
}

LoginForm.propTypes = {
  onLogin: PropTypes.func.isRequired
};

export default LoginForm;
