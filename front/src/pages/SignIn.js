import { Form, Input, Button, Checkbox } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import './signInCss.css';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
const Midle = styled.div`
display: flex;
    margin: auto;
    justify-content: center;
    height: 500px;
`
const SignIn = () => {
  const onFinish = (values) => {
    console.log('Received values of form: ', values);
  };

  return (
  <Midle>
    <Form
      name="normal_login"
      className="login-form"
      initialValues={{
        remember: true,
      }}
      onFinish={onFinish}
    >
      <Form.Item
        name="username"
        rules={[
          {
            required: true,
            message: 'Please input your Username!',
          },
        ]}
      >
        <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Username" />
      </Form.Item>
      <Form.Item
        name="password"
        rules={[
          {
            required: true,
            message: 'Please input your Password!',
          },
        ]}
      >
        <Input
          prefix={<LockOutlined className="site-form-item-icon" />}
          type="password"
          placeholder="Password"
        />
      </Form.Item>
      <Form.Item>
        <Button type="primary" htmlType="submit" className="login-form-button">
          sign in
        </Button>
        Or <Link to={"/signup"}>회원가입</Link>
      </Form.Item>
    </Form>
    </Midle>
  );
};

export default () => <SignIn />;